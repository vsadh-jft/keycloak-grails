import React from "react";
import { Button, Card, CardTitle, CardSubtitle, CardBody } from "reactstrap";
import Modal from "./Popup";

class UserList extends React.Component {
  constructor() {
    super();
    this.state = {
      modal: false
    };
    this.toggle = this.toggle.bind(this);
  }

  toggle() {
    this.setState(prevState => ({
      modal: !prevState.modal
    }));
  }

  renderView(activeTab) {
    return (
      <div>
        {"  "}
        {activeTab === "admin" && (
          <Button color="primary" onClick={this.toggle}>
            Add
          </Button>
        )}
      </div>
    );
  }

  render() {
    const { data, activeTab } = this.props;
    return (
      <div>
        {this.renderView(activeTab)}
        <div style={{ padding: "40px" }}>
          {data.length > 0 &&
            data.map((item, index) => (
              <Card style={{ maxWidth: "500px" }} key={index}>
                <CardBody>
                  <CardTitle>{item.title}</CardTitle>
                  <CardSubtitle>{item.body}</CardSubtitle>
                </CardBody>
              </Card>
            ))}
        </div>
        <Modal
          open={this.state.modal}
          toggle={this.toggle}
          handleInput={this.props.handleInput}
          submitForm={this.props.submitForm}
        />
      </div>
    );
  }
}

export default UserList;
